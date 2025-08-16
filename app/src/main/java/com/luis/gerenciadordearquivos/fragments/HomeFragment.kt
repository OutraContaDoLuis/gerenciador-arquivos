package com.luis.gerenciadordearquivos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.luis.gerenciadordearquivos.GridHomeButtonsAdapter
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.models.HomeButtonViewModel

class HomeFragment : Fragment() {

    private lateinit var cardInfoStorage : CardView
    private lateinit var gridViewButtons : GridView

    private var listOfHomeButtonViewModel : ArrayList<HomeButtonViewModel?> = ArrayList()
    private val listOfNameHomeButtonViewModel : ArrayList<String> = arrayListOf(
        "Imagens", "Videos", "Arquivos", "Audio", "Apps"
    )
    private val listOfDrawableHomeButtonViewModel : ArrayList<Int> = arrayListOf(
        R.drawable.ic_baseline_image_24, R.drawable.ic_baseline_video_file_24,
        R.drawable.ic_baseline_insert_drive_file_24, R.drawable.ic_baseline_audio_file_24,
        R.drawable.ic_baseline_apps_24
    )
    private val listOfOnClickHomeButtonViewModel : ArrayList<Fragment> = arrayListOf(
        ImageStorageFragment(), VideoStorageFragment(), DocumentStorageFragment(),
        AudioStorageFragment(), AppsFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        cardInfoStorage = view.findViewById(R.id.card_info_storage)
        gridViewButtons = view.findViewById(R.id.grid_buttons)

        return view
    }

    private fun goToFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.main_container, fragment)?.commit()
    }

    private fun setTheArrayListOfHomeButtonViewModel() {
        var size = listOfNameHomeButtonViewModel.size - 1
        while (size >= 0) {
            val homeButtonViewModel = HomeButtonViewModel()
            homeButtonViewModel.name = listOfNameHomeButtonViewModel[size].toString()
            homeButtonViewModel.drawable = listOfDrawableHomeButtonViewModel[size]
            listOfHomeButtonViewModel.add(homeButtonViewModel)
            size = size - 1
        }
        listOfHomeButtonViewModel =
            listOfHomeButtonViewModel.reversed() as ArrayList<HomeButtonViewModel?>
    }

    private fun onClickListenerItemInGridViewButtons(position : Int) {
        val fragmentSelected = listOfOnClickHomeButtonViewModel[position]
        goToFragment(fragmentSelected)
    }

    private fun setTheAdapterForGridViewButtons() {
        setTheArrayListOfHomeButtonViewModel()

        val gridHomeButtonsAdapter = GridHomeButtonsAdapter(context, listOfHomeButtonViewModel)
        gridViewButtons.adapter = gridHomeButtonsAdapter

        gridViewButtons.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            onClickListenerItemInGridViewButtons(position)
        }
    }

    override fun onStart() {
        super.onStart()
        cardInfoStorage.setOnClickListener { goToFragment(LocalStorageFragment()) }

        if (listOfHomeButtonViewModel.size != listOfNameHomeButtonViewModel.size)
            setTheAdapterForGridViewButtons()
    }
}