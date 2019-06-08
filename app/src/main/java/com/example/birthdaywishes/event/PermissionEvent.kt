package com.example.birthdaywishes.event

abstract class PermissionEvent

class PermissionNotGrantedEvent : PermissionEvent()

class PermissionGrantedEvent : PermissionEvent()