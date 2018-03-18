(ns prj.user
  (:require [prj.test :refer [test-clj test-cljs]]
            [prj.repl :refer [repl-cljs]]
            [prj.package :refer [update-pom deploy]]
            [prj.cljs :refer [npm-install build-cljs]]))

