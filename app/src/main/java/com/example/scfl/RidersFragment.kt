package com.example.scfl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scfl.databinding.FragmentRidersBinding
import org.jsoup.Jsoup


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RidersFragment : Fragment() {

    private val args: RidersFragmentArgs by navArgs()
    private var eventClassID = 0

    private var _binding: FragmentRidersBinding? = null

    private var riders: MutableList<Rider> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentRidersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventClass = args.eventClass

        if ("450" == eventClass) {
            eventClassID = 1
        } else if ("250" == eventClass) {
            eventClassID = 2
        }

        getEvents(eventClass)

        binding.buttonSecond.setOnClickListener {
            val action = RidersFragmentDirections.actionRidersFragmentToRaces450Fragment2(args.eventClass)
            findNavController().navigate(action)
        }

        binding.buttonSecond2.setOnClickListener {
            println(riders)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getEvents(eventClass: String) {
        Thread {
            try {
                getRiders(eventClass)
            } catch (e: java.lang.Exception) {
                println(e.message)
            }
            (activity as MainActivity).runOnUiThread {
                showRiders()
            }
        }.start()
    }

    private fun showRiders() {
        val list = riders.mapTo(arrayListOf()) { it.name }
        println(list)

        recyclerView = view?.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.layoutManager = layoutManager
        val adapter = RecyclerViewAdapter()
        adapter.setDataList(riders)

        val callback: ItemTouchHelper.Callback = RecyclerRowMoveCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)

        recyclerView!!.adapter = adapter
    }


    private fun getRiders(event: String) {

        //Parse the html to get the info that we need
        val url = "https://www.supercrosslive.com/riders/${event}"
        val doc = Jsoup.connect(url).get()
        val info = doc.getElementsByClass("driver-list-section").select("a")

        //for each rider found on the page create an instance of a Rider and add it to a list of riders
        info.forEach {
            val riderURL = "https://www.supercrosslive.com${it.attr("href")}"

            riders.add(createRider(riderURL))
        }
    }

    private fun createRider(url: String): Rider {
        val rider = Rider()
        val riderPage = Jsoup.connect(url).get()
        rider.name = riderPage.getElementsByClass("rider-title").text()
        rider.number = riderPage.getElementsByClass("profile-photo").text()
        val races = riderPage.getElementsByClass("driver-table")[1].select("tr")
        if (races.size >= 2) {
            rider.lastrace = races[races.size - 1].select("td:nth-child(6)").text()
        } else {
            rider.lastrace = "-"
        }
        rider.standing =
            riderPage.getElementsByClass("driver-table")[0].select("tr:nth-child(1)")
                .select("td:nth-child(3)").text()
        //val info = riderPage.getElementsByClass("listing-items").select("p")

        return rider
    }
}