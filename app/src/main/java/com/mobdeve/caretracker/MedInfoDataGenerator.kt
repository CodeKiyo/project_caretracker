package com.mobdeve.caretracker

class MedInfoDataGenerator {
    fun generateSampleData(): List<MedInfoModel> {
        return listOf(
            MedInfoModel(
                date = "10/07/24",
                weight = 86f,
                heartRate = 78,
                bloodPressure = "120/80",
                bodyTemp = 16.5f,
                respRate = 13,
                chiefComp = "Difficulty Being Alive",
                objectives = "Reinvigorate Will To Live",
                diagnosis = "Certified Idiot",
                plan = "Chest Compressions and a Girlfriend",
                comments = "Outlook not so good",
            ),
            MedInfoModel(
                date = "10/13/24",
                weight = 71.6f,
                heartRate = 91,
                bloodPressure = "100/70",
                bodyTemp = 31.5f,
                respRate = 19,
                chiefComp = "Sucks to Suck I Guess\n" +
                            "Don't die tho lol",
                objectives = "Two trucks having sex, Two trucks having sex, my muscles, my muscles, involuntarily flex.",
                diagnosis = "Watch your back pal, I'm made of snake oil and other dumb miscelanneous products.",
                plan = "Chest Compressions and a Sandwich",
                comments = "",
            )
        )
    }
}