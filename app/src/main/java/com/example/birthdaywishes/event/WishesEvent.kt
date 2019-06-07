package com.example.birthdaywishes.event

abstract class WishesEvent

class EmptyWishesEvent : WishesEvent()

class ValidWishesEvent : WishesEvent()