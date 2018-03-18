(ns jp.nijohando.test
  (:require #?(:clj  [clojure.test :as t :refer  [run-tests]]
               :cljs [cljs.test :as t :refer-macros [run-tests]])
            [jp.nijohando.deferable-test]
            #?(:cljs [jp.nijohando.deferable-test-cljs])))

(defn run
  []
  (run-tests #?@(:clj  ['jp.nijohando.deferable-test]
                 :cljs ['jp.nijohando.deferable-test 'jp.nijohando.deferable-test-cljs])))

