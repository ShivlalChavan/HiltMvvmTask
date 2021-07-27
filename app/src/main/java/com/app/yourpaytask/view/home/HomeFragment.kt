package com.app.yourpaytask.view.home

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.app.yourpaytask.R
import com.app.yourpaytask.databinding.FragmentHomeBinding
import com.app.yourpaytask.databinding.FragmentHomeBindingImpl
import com.app.yourpaytask.responsemodel.Data
import com.app.yourpaytask.view.base.BaseFragment
import com.app.yourpaytask.viewmodel.HomeViewModel
import com.arvind.newsapp.utils.Resource
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Response

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>()  {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initsview()
    }

    private fun initsview() = with(binding){


        val text : String = "<font color=#216491>your</font><font color=#009bac>pay</font>"
        title_tv.text = Html.fromHtml(text)
         getUserData()
         //setUpBottomSheet(binding)
          setUpSliderImage()
    }

    private fun setUpSliderImage() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.limited_image, "This is limited offer", ScaleTypes.CENTER_CROP))
        imageList.add(SlideModel(R.drawable.images_slide, "Your Pay has an offer",ScaleTypes.CENTER_CROP))

        val imageSlider = add_slider

        imageSlider.setImageList(imageList)
    }

    private fun setUpBottomSheet(binding: FragmentHomeBinding) {


        bottomSheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet_ll)

        bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, state: Int) {
                print(state)
                when (state) {

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.e("Hidden","BtmSheet")
                    }
                    BottomSheetBehavior.STATE_EXPANDED ->{
                        Log.e("expande","BtmSheet")
                    }
                    BottomSheetBehavior.STATE_COLLAPSED ->{
                    Log.e("collapsed","BtmSheet")
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

    }

    private fun getUserData() {
       viewModel.userData.observe(viewLifecycleOwner ,{ response->
           when(response){
                is Resource.Success -> {
                  progressBarStatus(false)
                  tryAgainStatus(false)
                   setUpData(response.data!!.data[0])
                }
               is Resource.Error -> {
                   tryAgainStatus(true, response.message!!)
                   progressBarStatus(false)
               }
               is Resource.Loading -> {
                   tryAgainStatus(false)
                   progressBarStatus(true)
               }
           }
       })
    }


    private fun setUpData(data: Data) {
        username_tv.text = data.name
        user_tv.text = data.email
    }


    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            tryAgainMessage.text = message
            tryAgainLayout.visibility = View.VISIBLE
            main_ll.visibility = View.GONE
        } else {
            tryAgainLayout.visibility = View.GONE
            main_ll.visibility = View.VISIBLE
        }

    }

    private fun progressBarStatus(status: Boolean) {
        progressBar.visibility = if (status) View.VISIBLE else View.GONE

    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater,container,false)


}