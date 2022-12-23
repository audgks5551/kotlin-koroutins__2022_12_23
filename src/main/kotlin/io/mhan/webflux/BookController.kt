package io.mhan.webflux

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    ) {

    @GetMapping
    fun getAll() : Flux<Book> {
        return bookService.getAll()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) : Mono<Void> {
        return bookService.delete(id);
    }

    @GetMapping("/{name}")
    fun getByName(@PathVariable name: String) : Mono<Book> {
        return bookRepository.findByName(name)
    }

    @PostMapping
    fun create(@RequestBody map: Map<String, Any>) : Mono<Book> {
        val book = Book (
            name = map["name"].toString(),
            price = map["price"] as Int
        )

        return bookRepository.save(book)
    }
}