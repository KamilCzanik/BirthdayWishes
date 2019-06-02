package com.example.birthdaywishes.di.wishes

import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.WishesFragment
import dagger.Component

@Component(modules = [WishesModule::class,DaoModule::class])
interface WishesComponent {
    fun inject(wishesFragment: WishesFragment)
}