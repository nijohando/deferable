(ns repl.cljs
  (:require [jp.nijohando.deferable :as f :include-macros true]
            [jp.nijohando.test :as test]
            [cljs.core.async :refer  [chan <! >! close! take!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))
