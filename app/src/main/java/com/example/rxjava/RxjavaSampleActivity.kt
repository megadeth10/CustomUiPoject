package com.example.rxjava

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.custom.activity.ToolbarActivity
import com.example.infinitylist.api.GetPosts
import com.example.infinitylist.data.Post
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.ActivityRxjavaTestBinding
import com.example.utils.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.observers.DefaultObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.Schedulers.single
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.DisposableHandle
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent
import retrofit2.Retrofit
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * TODO 2개의 서로 다른 thread로 처리한 결과를 listen하고 있다가 2개가 다 처리 되면 수행하는 기능 만들어 보기
 * solve: PublicSubject로 구현 가능을 확인함.
 */
class RxjavaSampleActivity : ToolbarActivity(), View.OnClickListener {
    lateinit var contentBinding: ActivityRxjavaTestBinding
    private val rxjavaGetPost: Retrofit by KoinJavaComponent.inject(Retrofit::class.java, named("RetrofitRxJava"))
    private val getPost: Retrofit by KoinJavaComponent.inject(Retrofit::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = RxjavaSampleActivity::class.simpleName
        this.setToolbar()
        this.setContent()
        this.setViewWidget()
        Log.e(TAG, "rxJavaGetPost $rxjavaGetPost")
        Log.e(TAG, "GetPost $getPost")
    }

    override fun onDestroy() {
        this.disposable.clear()
        super.onDestroy()
    }

    override fun onClick(p0: View?) {
        val id = p0?.id

        when (id) {
            R.id.simple_rxjava_Btn -> {
                this.rxjavaTest()
            }
            R.id.create_observer_Btn -> {
                this.getPost()
                this.createObserver()
            }
        }
    }

    private fun setToolbar() {
        this.binding.toolbar.title = "RxJava Test"
    }

    private fun setContent() {
        this.contentBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_rxjava_test, null, false)
        this.setContentsViewBinding(this.contentBinding)
    }

    private fun setViewWidget() {
        this.contentBinding.simpleRxjavaResultTV.text = "기본 rxjava"
        this.contentBinding.simpleRxjavaBtn.setOnClickListener(this)

        this.contentBinding.createObserverResultTV.text = "생성 rxjava"
        this.contentBinding.createObserverBtn.setOnClickListener(this)

        val idObserver = Observable.create(ObservableOnSubscribe<CharSequence> {
            this.contentBinding.idEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!it.isDisposed) {
                        it.onNext(p0)
                    }
                }
            })
        })
        idObserver.debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(object : Observer<CharSequence> {
                    override fun onComplete() {
                        Log.e(TAG, "idObserver onComplete()")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(t: CharSequence?) {
                        Log.e(TAG, "idObserver onNext() $t")
                    }

                    override fun onError(e: Throwable?) {

                    }
                })
        val passwordObserver = Observable.create(ObservableOnSubscribe<CharSequence> {
            this.contentBinding.passwordEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!it.isDisposed) {
                        it.onNext(p0)
                    }
                }
            })
        })
        passwordObserver.debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(object : Observer<CharSequence> {
                    override fun onComplete() {
                        Log.e(TAG, "passwordObserver onComplete()")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(t: CharSequence?) {
                        Log.e(TAG, "passwordObserver onNext() $t")
                    }

                    override fun onError(e: Throwable?) {
                    }
                })
        val BtnObserver = Observable.combineLatest(
                idObserver,
                passwordObserver,
                object : BiFunction<CharSequence, CharSequence, JoinObject> {
                    override fun apply(t1: CharSequence?, t2: CharSequence?): JoinObject? {
                        if (!t1.isNullOrEmpty() && !t2.isNullOrEmpty()) {
                            return JoinObject(t1, t2)
                        }
                        return null
                    }
                })
        BtnObserver.subscribe(object : Observer<JoinObject> {
            override fun onSubscribe(d: Disposable?) {
                disposable.add(d)
            }

            override fun onComplete() {

            }

            override fun onNext(t: JoinObject?) {
                Log.e(TAG, "BtnObserver onNext() $t")
                var state = false
                if (t != null) {
                    state = t.id!!.length >= 8 && t.pw!!.length >= 8
                }
                contentBinding.applyBtn.isEnabled = state
            }

            override fun onError(e: Throwable?) {

            }
        })

        val aSwitchObserverable = PublishSubject.create<Boolean>()
        val bSwitchObserverable = PublishSubject.create<Boolean>()
        aSwitchObserverable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean>{
                    override fun onComplete() {
                        Log.e(TAG, "aSwitch onComplete()")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(t: Boolean?) {
                        Log.e(TAG, "aSwitch onNext() $t")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "aSwitch error() ${e?.message}")
                    }
                })
        bSwitchObserverable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean>{
                    override fun onComplete() {
                        Log.e(TAG, "bSwitch onComplete()")
                    }

                    override fun onSubscribe(d: Disposable?) {
                        disposable.add(d)
                    }

                    override fun onNext(t: Boolean?) {
                        Log.e(TAG, "bSwitch onNext() $t")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "bSwitch error() ${e?.message}")
                    }
                })
        this.contentBinding.aSwitch.setOnCheckedChangeListener { compoundButton, b -> aSwitchObserverable.onNext(b) }
        this.contentBinding.bSwitch.setOnCheckedChangeListener { compoundButton, b -> bSwitchObserverable.onNext(b) }
        val combineSwitch = PublishSubject.combineLatest(aSwitchObserverable, bSwitchObserverable,
                BiFunction<Boolean, Boolean, String> { t1, t2 ->
                    Log.e(TAG, "combineSwitch BiFunction() a:$t1 b:$t2")
                    var result = "둘다 거짓"
                    if (t1 && t2) {
                        result = "둘다 참"
                    }

                    result
                })

        combineSwitch.subscribe(object : Observer<String>{
            override fun onComplete() {
                Log.e(TAG, "combineSwitch onComplete()")
            }

            override fun onSubscribe(d: Disposable?) {
                disposable.add(d)
            }

            override fun onNext(t: String?) {
                Log.e(TAG, "combineSwitch onNext() $t")
                contentBinding.combineSwitchTv.text = t
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, "combineSwitch error() ${e?.message}")
            }
        })
    }

    private inner class JoinObject(id: CharSequence?, pw: CharSequence?) {
        var id = id
        var pw = pw
    }

    private var disposable: CompositeDisposable = CompositeDisposable()

    // 기본적으로 Rxjava 사용법
    private fun rxjavaTest() {
        val observer = Observable.just("hello, world")
        val onNextAction: Consumer<String> = Consumer {
            Log.e(TAG, "$it")
        }
        val textDisposable = observer.map { it }
                .subscribe(onNextAction)
        disposable.add(textDisposable)
        val textDisposable2 = Observable.just("hello, world")
                .map {
                    "$it aaa"
                }
                .subscribe {
                    Log.e(TAG, "$it")
                }
        disposable.add(textDisposable2)
        val list = listOf<String>("a", "b", "c", "d", "e")
        val posable = Observable.fromIterable(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(Consumer { t -> Log.e(TAG, String.format("simple doOnNext() %s ", t)) })
                .doOnComplete { Log.e(TAG, String.format("simple onComplete()")) }
                .doOnError { Log.e(TAG, String.format("simple onError()")) }
                .map { String.format("%s ", it) }
                .subscribe(Consumer { this.contentBinding.simpleRxjavaResultTV.append(it) })
        disposable.add(posable)
    }

    // Observable과 observer를 분리 하여 선언
    private fun createObserver() {
        var postList: ArrayList<Post> = getPost()
        val postObservable = Observable.create(ObservableOnSubscribe<Post>() {
            try {
                if (!it.isDisposed) {
                    postList.forEach { post -> it.onNext(post) }
                }
                if (!it.isDisposed) {
                    it.onComplete()
                }
            } catch (e: Exception) {
                it.onError(e)
            }
        })
        val postObserver = postObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Log.e(TAG, String.format("onNext()")) }
                .doOnError { Log.e(TAG, String.format("onError()")) }
                .doOnComplete { Log.e(TAG, String.format("onComplete()")) }
                .subscribe {
                    val title = "${it.title} "
                    this.contentBinding.createObserverResultTV.append(title)
                }
        disposable.add(postObserver)
    }

    private fun getPost(): ArrayList<Post> {
        val list: ArrayList<Post> = ArrayList(0)
        for (i in 0..10) {
            val post = Post();
            post.title = "index $i"
            post.author = "index $i"
            post.id = i
            list.add(post)
        }
        return list
    }

}