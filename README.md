# Deferable

Deferable is clojure / clojurescript library that provides the deferred function like golang's 'defer'.


## Usage

#### Deferred functions

`do*` block works similarly to the clojure `do` and `defer` can be used inside the block.
`defer` function is scheduled to be evaluated before the block returns value.

```clojure
repl.clj=> (require '[jp.nijohando.deferable :refer [do* defer]])
repl.clj=> (do* (defer (println "2")) (println "1"))
1
2
```

Multiple `defer` forms are evaluated in LIFO order.

```clojure
repl.clj=> (require '[jp.nijohando.deferable :refer [do* defer]])
repl.clj=> (do* (defer (println "3")) (defer (println "2")) (println "1"))
1
2
3
```

#### Deferred functions in async


`do**` block is similar to `do*` but designed for async programming like core.async.
Difference with`do*` is that Deferred functions are evaluated when calling `done` function passed to the first argument of the `do**` block.

```clojure
repl.clj=> (require '[jp.nijohando.deferable :refer [do** defer]])
repl.clj=> (require '[clojure.core.async :refer [go]])
repl.clj=> (do** done (defer (println "3")) (go (println "2") (done)) (println "1"))
1
2
3
```


