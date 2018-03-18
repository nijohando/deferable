(ns jp.nijohando.deferable)

(def ^:dynamic *context* nil)

(defmacro defer
  [& forms]
  `(do
     (assert *context* "defer used not in (do* ...) or (do** ...) block")
     (swap! *context* conj (delay ~@forms)) nil))

(defmacro do**
  [done-sym & forms]
  `(let [ctx# (atom [])
         ~done-sym (fn []
                     (doseq [x# (reverse @ctx#)]
                       @x#))]
     (binding [*context* ctx#]
       ~@forms)))

(defmacro do*
  [& forms]
  `(do** done#
     (try
       ~@forms
       (finally (done#)))))
