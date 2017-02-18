(ns jp.nijohando.deferable)

(def ^:dynamic *context* nil)

(defn done*
  [ctx]
  (when ctx
    (doseq [x (reverse @ctx)]
      @x)))

#? (:clj [

(defmacro defer
  [& forms]
  `(do
     (assert *context* "defer used not in (do* ...) or (do** ...) block")
     (swap! *context* conj (delay ~@forms)) nil))

(defmacro do**
  [done-sym & forms]
  `(let [ctx# (atom [])
         ~done-sym (fn [] (done* ctx#))]
     (binding [*context* ctx#]
     ~@forms)))

(defmacro do*
  [& forms]
  `(binding [*context* (atom [])]
     (try ~@forms
       (finally (done* *context*)))))

])

