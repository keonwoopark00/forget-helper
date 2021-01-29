package com.edgar.forgethelper.adapter

/**
 * OnItemClickListener.kt
 * click listener interface for location and section adapters
 */

interface OnItemClickListener<T> {
    fun onNameClick(item: T)
    fun onUpdateClick(item: T)
    fun onDeleteClick(item: T)
}