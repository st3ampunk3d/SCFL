package com.example.scfl

class Event(event: String) {
    private val eventClass = event
    public var riders: MutableList<Rider> = mutableListOf()
    private val picks: MutableList<Rider> = mutableListOf()


    //Print a human readable table showing each rider and their info
    fun printTable() {
        println("|**********************************************************|")
        println("| ID | Rider                   |  #  | Last Race | Overall |")

        riders.forEachIndexed(){ index, rider ->
            println("|----------------------------------------------------------|")
            print("|${" ".repeat(3-(index+1).toString().length)}${index+1} " )
            print("| ${rider.name}${" ".repeat(24-rider.name.length)}")
            print("|${" ".repeat(4-rider.number.length)}${rider.number} ")
            print("|${" ".repeat(10-rider.lastrace.length)}${rider.lastrace} ")
            println("|${" ".repeat(8-rider.standing.length)}${rider.standing} |")
        }
        println("|**********************************************************|")
    }

    //Get the user's top 10 picks. Validate the input after each one.
    fun makeSelections() {
        println("Pick your favorites!")
        println("Enter the rider ID's for your top ten choices.")

        print("\n1st:  ")
        validateSelection(readln()!!, "1st")
        print("2nd:  ")
        validateSelection(readln()!!, "2nd")
        print("3rd:  ")
        validateSelection(readln()!!, "3rd")
        print("4th:  ")
        validateSelection(readln()!!, "4th")
        print("5th:  ")
        validateSelection(readln()!!, "5th")
        print("6th:  ")
        validateSelection(readln()!!, "6th")
        print("7th:  ")
        validateSelection(readln()!!, "7th")
        print("8th:  ")
        validateSelection(readln()!!, "8th")
        print("9th:  ")
        validateSelection(readln()!!, "9th")
        print("10th: ")
        validateSelection(readln()!!, "10th")
    }

    //Make sure the input is a number within the limits of the list.
    //A rider can't place first and third. Make sure there are no duplicate picks.
    //If there are any issues restart the validation on that pick until it passes.
    private fun validateSelection(rider: String, pick: String) {
        if (rider.toIntOrNull() == null || rider.toInt() !in 1..riders.size) {
            println("Invalid Input!")
            print("${pick}: ")
            validateSelection(readln()!!, pick)
        }
        else if (riders[rider.toInt()-1] in picks) {
            println("You've already picked this rider. Try again.")
            print("${pick}: ")
            validateSelection(readln()!!, pick) //I really like recursion. It makes things like validation simple.
        }
        else {
            picks.add(riders[rider.toInt()-1])
        }
    }

    //Display a table showing the user's top 10 picks.
    public fun printSelections() {
        println("Your picks are:")
        println("|**************************************|")
        println("| PICK | Rider                   |  #  |")

        picks.forEachIndexed(){ index, rider ->
            println("|--------------------------------------|")
            print("|${" ".repeat(4-(index+1).toString().length)}${index+1}  " )
            print("| ${rider.name}${" ".repeat(24-rider.name.length)}")
            println("|${" ".repeat(4-rider.number.length)}${rider.number} |")
        }
        println("|**************************************|")
    }
}