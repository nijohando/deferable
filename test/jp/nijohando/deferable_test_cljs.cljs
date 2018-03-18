(ns jp.nijohando.deferable-test-cljs
  (:require [cljs.test :as t :refer-macros [run-tests is are deftest testing async]]
            [jp.nijohando.deferable :as d :include-macros true]
            [cljs.core.async :as as])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(deftest do**-tests
  (testing "Defer form can be called asynchronously"
    (async end
      (let [x (atom [])]
        (d/do** done
          (let [ch (as/chan 1)
                _ (d/defer
                    (swap! x conj :closed)
                    (as/close! ch))]
            (go
              (let [_ (as/>! ch :d1)
                    r (as/<! ch)]
                (done)
                (is (= :d1 r))
                (is (= [:closed] @x))
                (end)))))))))

(deftest do*-with-go-tests
  (testing "do* can be used in (go ...) block"
    (async end
      (go
        (let [x (atom [])]
          (d/do*
            (let [ch (as/chan 1)
                  _ (swap! x conj :d1)
                  _ (d/defer (swap! x conj :closed)
                             (as/close! ch))
                  _ (as/>! ch :d1)
                  _ (swap! x conj :d2)
                  r (as/<! ch)
                  _ (swap! x conj :d3)]
              (swap! x conj :d4)
              (is (= :d1 r))))
          (is (= [:d1 :d2 :d3 :d4 :closed] @x))
          (end))))))

(deftest do**-with-go-tests
  (testing "do** can be used in (go ...) block"
    (async end
      (go
        (d/do** done
          (let [x (atom [])
                ch (as/chan 1)
                _ (d/defer (swap! x conj :closed)
                           (as/close! ch))
                _ (as/>! ch :d1)
                r (as/<! ch)]
            (done)
            (is (= :d1 r))
            (is (= [:closed] @x))
            (end)))))))

