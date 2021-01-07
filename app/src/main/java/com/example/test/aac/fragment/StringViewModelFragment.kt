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
 * TODO: Fragment에서 viewModel이 activity lifecycle을 보장하지 않음 무엇이 문제인가?
 * ViewModelProview에 lifecycleOwner가 activity로 할수 없나
 */
class StringViewModelFragment : Fragment(){
    private var _binding: FragmentBidingBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: StringViewModel
    private val TAG = StringViewModelFragment::class.simpleName
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e(TAG,"onActivityCreated()")
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(StringViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(StringViewModel::class.java)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = activity

        // ref http://pluu.github.io/blog/android/2020/01/25/android-fragment-lifecycle/
        // fragment는 viewLifecycleOwner 사용 권장
        viewModel?.data.observe(viewLifecycleOwner, Observer {
            binding?.display?.text = it
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"onCreateView()")
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_biding,container, false)
        return binding?.root
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
            viewModel?.data?.value = strBuffer.toString()
        }
    }
}