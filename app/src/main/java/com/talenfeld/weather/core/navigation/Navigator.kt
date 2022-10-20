package com.talenfeld.weather.core.navigation

import androidx.appcompat.app.AppCompatActivity


interface Navigator {
    fun perform(command: RouterCommand)
}

class ActivityNavigator: Navigator {

    private var hostActivity: AppCompatActivity? = null

    private val pendingCommands: MutableList<RouterCommand> = ArrayList()

    fun attach(activity: AppCompatActivity) {
        hostActivity = activity
        pendingCommands.run {
            forEach { command -> command.route(activity) }
            clear()
        }
    }

    fun detach() {
        hostActivity = null
    }

    override fun perform(command: RouterCommand) {
        hostActivity?.let(command::route) ?: pendingCommands.add(command)
    }
}
