package com.example.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var tvResult: TextView
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result->
        if (result.resultCode == PickNumberActivity.RESULT_CODE && result.data != null){
            val selectedValue = result.data?.getIntExtra(PickNumberActivity.EXTRA_SELECTED_VALUE, 0)
            tvResult.text = "You choose a number: $selectedValue"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCategory: Button = view.findViewById(R.id.btn_category)
        btnCategory.setOnClickListener(this)
        val btnPickNumber: Button = view.findViewById(R.id.btn_pick_number)
        btnPickNumber.setOnClickListener(this)

        tvResult = view.findViewById(R.id.tv_result)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_category) {
            val mFragmentManager = parentFragmentManager
            val mCategoryFragment = CategoryFragment()
            mFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
            }
        } else if (v.id == R.id.btn_pick_number) {
            val pickNumberIntent = Intent(requireActivity(), PickNumberActivity::class.java)
            resultLauncher.launch(pickNumberIntent)
        }

    }

}