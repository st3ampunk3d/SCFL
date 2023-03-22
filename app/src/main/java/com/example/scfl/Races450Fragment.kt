package com.example.scfl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.scfl.databinding.FragmentRaces450Binding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Races450Fragment : Fragment() {

    private val args: Races450FragmentArgs by navArgs()
    private var eventClassID = 0

    private var _binding: FragmentRaces450Binding? = null

    private var races: MutableList<Race> = mutableListOf()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentRaces450Binding.inflate(inflater, container, false)
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

        getEvents()



        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_races450Fragment_to_FirstFragment)
        }

        binding.buttonSecond2.setOnClickListener {
            getEvents()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getEvents() {
        Thread {
            try {
                getRaces(eventClassID)
            } catch (e: java.lang.Exception) {
                println(e.message)
            }
            (activity as MainActivity).runOnUiThread {
                showRaces()
            }
        }.start()
    }

    private fun showRaces() {
        val arrayAdapter: ArrayAdapter<*>
        val list = races.mapTo(arrayListOf()) { it.name }
        println(list)

        // access the listView from xml file
        val mListView = view?.findViewById<ListView>(R.id.listView)
        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, list
        )
        mListView?.adapter = arrayAdapter

        mListView?.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            //val selectedItem = parent.getItemAtPosition(position) as String

            val action = Races450FragmentDirections.actionRaces450FragmentToRidersFragment(args.eventClass, races[position].name)

            findNavController().navigate(action)
        }
    }

    private fun getRaces(id: Int) {
        val eventsUrl = "https://www.supercrosslive.com/ama-supercross-historical-results"

        val doc = Jsoup.connect(eventsUrl).get()
        val info = doc.getElementsByClass("tr-historical-class").select("tr")
        val hasResults =  info.select("tr").select("td").select("a")

        var pastEvents = 0
        hasResults.forEach {
            if ("2023" in it.attr("href")) {
                pastEvents += 1
            }
        }

        info.forEachIndexed { position, it ->
            val row = it.select("tr").select("td")[0].text()

            if (row != "") {
                val x = Race()
                x.name = row

                x.number = (position+1)*5

                var finalResultsUrl = "hello"

                val eventResults =  it.select("tr").select("td")[0].select("a")
                val eventResultsPage = Jsoup.connect("https://www.supercrosslive.com/${eventResults.attr("href")}").get()

                if (position <= pastEvents) {
                    val eventDate = eventResultsPage.getElementById("eventdates")?.text()
                    x.date = LocalDate.parse(eventDate!!.lowercase().replaceFirstChar { it.uppercase() }, DateTimeFormatter.ofPattern("MMMM d, yyyy")).toString()

                    val buttons = eventResultsPage.getElementsByClass("resultlink").select("a")

                    buttons.forEach {
                        if (it.text() == "Official Results" && "S${id}F1" in it.attr("href")){
                            finalResultsUrl = it.select("a").attr("href")
                        }
                    }
                }
                else {
                    val datesPage = Jsoup.connect("https://www.supercrosslive.com/tickets").get()
                    val eventDate = datesPage.getElementsByClass("round-title")[position-pastEvents-1].select("span")[2].text().split(" / ")[1]
                    x.date = LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("MMM dd, yyyy")).toString()
                }

                if (LocalDate.now().isAfter(LocalDate.parse(x.date))) {
                    val resultPage = Jsoup.connect(finalResultsUrl).get()
                    val resultsTable = resultPage.getElementsByClass("responsive-table").select("tbody")[0].select("tr").select("td:nth-child(2)")

                    resultsTable.forEach {res ->
                        x.results.add(res.text())
                    }
                }

                    races.add(x)
            }
        }
        val json = Json.encodeToString(races)
        println(json)
    }
}