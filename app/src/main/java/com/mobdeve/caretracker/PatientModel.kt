package com.mobdeve.caretracker

class PatientModel (patientName: String,
                    patientAge: Int,
                    patientRoom: Int,
                    patientBirthdate: String,
                    patientCivilStatus: String,
                    patientECN: String,
                    patientECNo: String,
                    patientEmail: String,
                    patientMobileno: String,
                    patientNationality: String,
                    patientReligion: String,
                    patientSex: String) {
    var patientName = patientName
        private set
    var patientAge = patientAge
        private set
    var patientRoom = patientRoom
        private set
    var patientBirthdate = patientBirthdate
        private set
    var patientCivilStatus = patientCivilStatus
        private set
    var patientECN = patientECN
        private set
    var patientECNo = patientECNo
        private set
    var patientEmail = patientEmail
        private set
    var patientMobileno = patientMobileno
        private set
    var patientNationality = patientNationality
        private set
    var patientReligion = patientReligion
        private set
    var patientSex = patientSex
        private set

    override fun toString(): String {
        return "UserModel{" +
                "patientName='" + patientName + '\'' +
                ", patientAge=" + patientAge +
                ", patientRoom=" + patientRoom +
                ", patientBirthdate=" + patientBirthdate +
                ", patientCivilStatus=" + patientCivilStatus +
                ", patientECN=" + patientECN +
                ", patientECNo=" + patientECNo +
                ", patientEmail=" + patientEmail +
                ", patientMobileno=" + patientMobileno +
                ", patientNationality=" + patientNationality +
                ", patientReligion=" + patientReligion +
                ", patientSex=" + patientSex +
                '}'
    }
}