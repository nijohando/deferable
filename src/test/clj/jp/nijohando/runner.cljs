(ns jp.nijohando.runner
  (:require [clojure.string :as str]
            [cljs.test :as t]
            [jp.nijohando.test :as test]))

(enable-console-print!)

(defn -main []
  (test/run))

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (when-not (t/successful? m)
    ((aget js/process "exit") 1)))

(set! *main-cli-fn* -main)
