package com.app.cameraxwork.mvccamera.ui

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import com.app.cameraxwork.R
import com.app.cameraxwork.databinding.LayoutLprItemUpdateDialogBinding
import com.app.cameraxwork.mvccamera.model.Lpr
import com.app.cameraxwork.mvccamera.util.VSLoadImage
import com.google.gson.GsonBuilder

class LprItemUpdateDialog : DialogFragment() {

    interface DialogInteraction {
        fun onApply(position: Int, item: Lpr)
        fun onCancel(position: Int, item: Lpr)
    }

    companion object {

        const val TAG = "LprItemUpdateDialog"
        private const val ITEM_POS = "ITEM_POS"
        private const val LPR_ITEM = "LPR_ITEM"

        fun newInstance(position: Int, lpr: Lpr): LprItemUpdateDialog {
            val args = Bundle()
            args.putInt(ITEM_POS, position)
            args.putString(LPR_ITEM, GsonBuilder().create().toJson(lpr))
            val fragment = LprItemUpdateDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private var position: Int = 0
    private lateinit var item: Lpr

    private var interaction: DialogInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = requireArguments().getInt(ITEM_POS)
        val temp = requireArguments().getString(LPR_ITEM)
        item = GsonBuilder().create().fromJson(temp, Lpr::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interaction = context as DialogInteraction
    }

    private lateinit var viewBinding: LayoutLprItemUpdateDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = LayoutLprItemUpdateDialogBinding.inflate(
            inflater,
            container,
            false
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView() {
        VSLoadImage.getInstance()
            .execute(item.vehicleImage, viewBinding.ImageLPScanCar)

        var mDataSpinner = ArrayList<FSData>()
        mDataSpinner.add(
            FSData(
                1,
                "Registration Number"
            )
        )
        mDataSpinner.add(
            FSData(
                2,
                "Engine Number"
            )
        )
        mDataSpinner.add(
            FSData(
                3,
                "Chassis Number"
            )
        )
        mDataSpinner.add(
            FSData(
                4,
                "Contract Number"
            )
        )

        viewBinding.edtRegistrationNumber.setText(setTextDefault(item.outputValue))
        val adapter: FRAdapterData =
            FRAdapterData(requireActivity(), 17367048)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        adapter.addAll(mDataSpinner)
        viewBinding.screenScanPlatesDetailSpnNumber.setAdapter(adapter)

        for (i in mDataSpinner.indices) {
            val fsData: FSData =
                mDataSpinner.get(i) as FSData
            if (fsData.getName().equals(item.selectedData, ignoreCase = true)) {
                viewBinding.screenScanPlatesDetailSpnNumber.setSelection(i)
                break
            }
        }

        viewBinding.screenScanPlatesDetailSpnNumber.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                /*mDataSelected =
                    adapterView.getItemAtPosition(i) as FSData*/
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
//        viewBinding.edtRegistrationNumber.setFilters(this.inputFilters)
    }

    private fun setTextDefault(strData: String): String? {
        return if (strData.equals(
                "UNREG/NA",
                ignoreCase = true
            )
        ) "" else strData
    }

    /* var inputFilters = arrayOf(InputFilter { src, start, end, dst, dstart, dend ->
         if (src == "") {
             src
         } else {
             (if (src.toString().matches("[a-zA-Z0-9]+")) src else "") as CharSequence
         }
     }, InputFilter.AllCaps())*/

    private fun setupClickListeners() {
        viewBinding.ButtonLPScanApply.setOnClickListener {
            // TODO: Do some task here
            dismiss()
            interaction?.onApply(position, item)
        }
        viewBinding.ButtonLPScanCancel.setOnClickListener {
            // TODO: Do some task here
            dismiss()
            interaction?.onApply(position, item)
        }

    }


}