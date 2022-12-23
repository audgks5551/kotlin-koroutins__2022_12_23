package io.mhan.webflux

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.atomic.AtomicLong

@Service
class BookService {

    private final val nextId = AtomicLong(0)

    val books : MutableList<Book> = mutableListOf(
        Book(id = nextId.incrementAndGet(), name = "코틀린 인 액션", price = 30000),
        Book(id = nextId.incrementAndGet(), name = "HTTP 완벽 가이드", price = 40000)
    )

    fun getAll() : Flux<Book> {
        return books.toFlux()
    }

    fun get(id: Long): Mono<Book> {
        return books.find { it.id == id }.toMono();
    }

    fun add(request: Map<String, Any>): Mono<Book> {
        return Mono.just(request)
            .map { map ->
                val book = Book(
                    id = nextId.incrementAndGet(),
                    name = map["name"].toString(),
                    price = map["price"] as Int
                )

                books.add(book)
                book
            }
    }

    fun delete(id: Long): Mono<Void> {
        return Mono.justOrEmpty(books.find { it.id == id })
            .map { books.remove(it) }
            .then()
    }
}