package com.mobdeve.caretracker

class UserModel (Name: String) {
    var Name = Name
        private set
    override fun toString(): String {
        return "UserModel{" +
                "Name='" + Name +
                '}'
    }
}