package com.example.test.aac.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.aac.viewmodel.StringViewModel
import com.example.test.myapplication.R
import com.example.test.myapplication.databinding.FragmentBidingBinding
import com.example.utils.Log
import java.lang.StringBuilder

/**
 * TODO: Fragment back stack과 관련하여 수정 fragment와 입력 fragment로
 * ViewModel이 공유하도록 추가 테스트 필요하다.
 * TODO: input_layout에 viewBindingIgnore를 설정 하였지만 Bindging 객체에 생성이 된다. 이것이 맞는건가?
 */
class StringViewModelFragment : Fragment(){
    private var _binding: FragmentBidingBinding? = null
    private val binding get() = _binding
    lateinit var viewModel: StringViewModel
    private val TAG = StringViewModelFragment::class.simpleName
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e(TAG,"onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
        // Framgment는 당연히 lifecycleOwner가 activity이니 requireActivity로 설정 해준다.
//        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(StringViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(StringViewModel::class.java)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = activity

        // ref http://pluu.github.io/blog/android/2020/01/25/android-fragment-lifecycle/
        // fragment는 viewLifecycleOwner 사용 권장
        viewModel.data.observe(viewLifecycleOwner, Observer {
            binding?.display?.text = it
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"onCreateView()")
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_biding,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG,"onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        binding?.insertBtn?.setOnClickListener {
            this.appendOnClick()
        }
    }

    override fun onDestroyView() {
        Log.e(TAG,"onDestroyView()")
        super.onDestroyView()
        // ref: https://developer.android.com/topic/libraries/view-binding
        // 문서에 null 처리 해야함.
        _binding = null
    }

    private fun appendOnClick(){
        Log.e(TAG,"appendOnClick()")
        val additionalText = binding?.input?.text.toString()
        if(additionalText.isNotEmpty()){
            val text: String? = binding?.viewModel?.data?.value ?: ""
            val strBuffer = text?.let { it1 -> StringBuilder(it1) }
            if(strBuffer?.isNotEmpty() != null){
                strBuffer.append("\n")
            }
            strBuffer?.append(additionalText)
            viewModel.data.value = strBuffer.toString()
        }
    }
}