package com.shop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShopApplication{
	companion object{
		@JvmStatic fun main(args: Array<String>) {
			runApplication<ShopApplication>(*args)
		}
	}
}




