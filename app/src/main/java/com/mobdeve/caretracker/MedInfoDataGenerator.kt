package com.mobdeve.caretracker

class MedInfoDataGenerator {
    fun generateSampleData(): List<MedInfoModel> {
        return listOf(
            MedInfoModel(
                heartRate = 78,
                bloodPressure = 16.6f,
                bodyTemp = 16.5f,
                respRate = 10.5f,
                chiefComp = "Difficulty Being Alive",
                objectives = "Reinvigorate Will To Live",
                diagnosis = "Certified Idiot",
                plan = "Chest Compressions and a Girlfriend",
                comments = "Outlook not so good",
            ),
            MedInfoModel(
                heartRate = 91,
                bloodPressure = 28.6f,
                bodyTemp = 31.5f,
                respRate = 13.5f,
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