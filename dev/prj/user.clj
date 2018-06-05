(ns prj.user
  (:require [jp.nijohando.deferable :as d]
            [jp.nijohando.prj.test :as prj-test]
            [prj.cljs]))

(defn test-clj
  []
  (prj-test/run-tests 'jp.nijohando.deferable-test))

(defn test-cljs
  []
  (prj.cljs/test-cljs))

(defn repl-cljs
  []
  (prj.cljs/repl-cljs))
