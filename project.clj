(defproject jp.nijohando/deferable "0.1.0"
  :description "Finally block free deferred function utilities"
  :url "https://github.com/nijohando/deferable"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.6.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]]

  :plugins [[lein-figwheel "0.5.4-7"]
            [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src/main/clj"]

  :clean-targets ^{:protect false} ["src/dev/resources/public/js/compiled"
                                    "src/test/resources/private/js/compiled"
                                    "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/main/clj" "src/dev/clj" "src/test/clj"]
                :figwheel {:open-urls ["http://localhost:3449/index.html"]}
                :compiler {:main repl.cljs
                           :asset-path "js/compiled/out"
                           :output-to "src/dev/resources/public/js/compiled/main.js"
                           :output-dir "src/dev/resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               {:id "test"
                :source-paths ["src/main/clj" "src/test/clj"]
                :compiler {:main jp.nijohando.runner
                           ;:asset-path "js/compiled/out"
                           :target :nodejs
                           :output-to "src/test/resources/private/js/compiled/main.js"
                           ;:output-dir "src/dev/resources/private/js/compiled/out"
                           :optimizations :simple}}]
              :test-commands {"unit" ["node"
                                      "src/test/resources/private/js/compiled/main.js"]}}


  :figwheel {:css-dirs ["src/dev/resources/public/css"]
             :server-logfile "logs/gwheel_server.log"}

  :repl-options {:init-ns repl.clj}
  :profiles {:dev {:dependencies [[binaryage/devtools "0.7.2"]
                                  [figwheel-sidecar "0.5.4-7"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.namespace "0.3.0-alpha3"]
                                  [org.clojure/core.async "0.2.385"]]
                   :source-paths ["src/main/clj" "src/dev/clj" "src/test/clj"]
                   :resource-paths ["src/dev/resources"]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init (set! *print-length* 50)
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :aliases {"clj-test"  ["do" "test" "jp.nijohando.deferable-test"]
            "cljs-test" ["do" ["cljsbuild" "test" "unit"]]})
