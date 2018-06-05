# Deferable

[![Clojars Project](https://img.shields.io/clojars/v/jp.nijohando/deferable.svg)](https://clojars.org/jp.nijohando/deferable)
[![CircleCI](https://circleci.com/gh/nijohando/deferable.svg?style=shield)](https://circleci.com/gh/nijohando/deferable)

Clojure(Script) deferred function library like golang's `defer`.

## Rationale

Main motivations for writing this library are:

* Providing deferred functions like golang's `defer`
* Available on both Clojure and ClojureScript

## Installation

#### Ligningen / Boot

```clojure
[jp.nijohando/deferable "0.2.1"]
```

#### Clojure CLI / deps.edn

```clojure
jp.nijohando/deferable {:mvn/version "0.2.1"}
```

## Usage

Clojure

```clojure
(require '[jp.nijohando.deferable :as d])
```

ClojureScript

```clojure
(require '[jp.nijohando.deferable :as d :include-macros true])
```


#### Deferred functions

`do*` block works similarly to the clojure `do` and `defer` can be used inside the block.  
`defer` function is scheduled to be evaluated before the block returns value.

```clojure
(d/do* 
  (d/defer (println "2")) 
  (println "1"))
;;=> "1"
;;=> "2"
;;=> nil
```

Multiple `defer` forms are evaluated in LIFO order.

```clojure
(d/do* 
  (d/defer (println "3")) 
  (d/defer (println "2")) 
  (println "1"))
;;=> "1"
;;=> "2"
;;=> "3"
```

#### Deferred functions in async


`do**` block is similar to `do*` but designed for async programming like core.async.  
Difference with`do*` is that Deferred functions are evaluated when calling `done` function passed to the first argument of the `do**` block.

```clojure
(require '[clojure.core.async :refer [go]])
(d/do** done 
  (d/defer (println "3")) 
  (go 
    (println "2") 
    (done)) 
  (println "1"))
;;=> "1"
;;=> "2"
;;=> "3"
```

## License

© 2017-2018 nijohando  

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

