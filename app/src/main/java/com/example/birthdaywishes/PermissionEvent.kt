package com.example.birthdaywishes

abstract class PermissionEvent

class PermissionNotGrantedEvent : PermissionEvent()

class PermissionGrantedEvent : PermissionEvent()