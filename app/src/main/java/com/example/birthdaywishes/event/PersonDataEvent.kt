package com.example.birthdaywishes.event

abstract class PersonDataEvent

class InvalidPersonDataEvent : PersonDataEvent()

class ValidPersonDataEvent : PersonDataEvent()

