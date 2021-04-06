package com.sainsburys.pd.kotlinhello

import org.springframework.data.relational.core.mapping.Table

@Table
data class People(var id: Long, var name: String, var greeting: String)
