(ns prj.user
  (:require [jp.nijohando.deferable :as d :include-macros true]
            [jp.nijohando.deferable-test]
            [jp.nijohando.deferable-test-cljs]
            [cljs.test :refer-macros [run-tests]]))

(defn test-cljs
  []
  (run-tests 'jp.nijohando.deferable-test
             'jp.nijohando.deferable-test-cljs))
