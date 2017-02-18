(ns jp.nijohando.deferable-test
  (:require #?(:clj  [clojure.test :as t :refer [run-tests is are deftest testing]]
               :cljs [cljs.test :as t :refer-macros [run-tests is are deftest testing async]])
            [jp.nijohando.deferable :as d :include-macros true]
            #?(:clj [clojure.core.async :as as] :cljs [cljs.core.async :as as]))
  #?(:cljs (:require-macros [cljs.core.async.macros :refer [go]])))

(deftest do*-tests
  (testing "Evaluated value is returned"
    (is (= 1 (d/do* 1)))
    (is (= 2 (d/do* (inc 1)))))
  (testing "Deferred form is called at the end of (do* ...) block"
    (let [x (atom [])]
      (is (= 1 (d/do*
                 (d/defer (swap! x conj :d1))
                 1)))
      (is (= [:d1] @x))))
  (testing "Deferred forms are called in FILO order"
    (let [x (atom [])]
      (is (= 2 (d/do*
                 (d/defer (swap! x conj :d1))
                 (d/defer (swap! x conj :d2))
                 (d/defer (swap! x conj :d3))
                 (swap! x conj :d4)
                 2)))
      (is (= [:d4 :d3 :d2 :d1] @x))))
  (testing "Nested blocks are managed properly"
    (let [x (atom [])
          y (atom [])]
      (is (= 1 (d/do*
                 (d/defer (swap! x conj :d1))
                 (d/defer (swap! x conj :d2))
                 (d/defer (swap! x conj :d3))
                 (swap! x conj :d4)
                 (d/do*
                   (d/defer (swap! y conj :d5))
                   (d/defer (swap! y conj :d6))
                   (d/defer (swap! y conj :d7))
                   (swap! y conj :d8)
                   1))))
      (is (= [:d4 :d3 :d2 :d1] @x))
      (is (= [:d8 :d7 :d6 :d5] @y)))))

(deftest do**-tests
  (testing "Evaluated value is returned"
    (is (= 1 (d/do** done 1)))
    (is (= 2 (d/do** done (inc 1)))))
  (testing "Deferred form is not called without applying 'done' function"
    (let [x (atom [])]
      (is (= 1 (d/do** done
                 (d/defer (swap! x conj :d1))
                 1)))
      (is (= [] @x))))
  (testing "Deferred form is called with applying 'done' function"
    (let [x (atom [])]
      (is (= 1 (d/do** done
                 (d/defer (swap! x conj :d1))
                 (done)
                 1)))
      (is (= [:d1] @x))))
  (testing "Nested blocks are managed properly"
    (let [x (atom [])
          y (atom [])]
      (is (= 1 (d/do** done1
                 (d/defer (swap! x conj :d1))
                 (d/defer (swap! x conj :d2))
                 (d/defer (swap! x conj :d3))
                 (swap! x conj :d4)
                 (d/do** done2
                   (d/defer (swap! y conj :d5))
                   (d/defer (swap! y conj :d6))
                   (d/defer (swap! y conj :d7))
                   (swap! y conj :d8)
                   (done1)
                   (done2)
                   1))))
      (is (= [:d4 :d3 :d2 :d1] @x))
      (is (= [:d8 :d7 :d6 :d5] @y)))))

