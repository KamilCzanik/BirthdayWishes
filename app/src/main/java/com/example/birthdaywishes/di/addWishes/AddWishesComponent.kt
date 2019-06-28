package com.example.birthdaywishes.di.addWishes

import com.example.birthdaywishes.di.dao.DaoModule
import com.example.birthdaywishes.ui.AddWishesFragment
import dagger.Component

@Component(modules = [AddWishesModule::class,DaoModule::class])
interface AddWishesComponent {
    fun inject(fragment: AddWishesFragment)
}