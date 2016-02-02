 (ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn my-first
  [[first-thing]] ; Notice that first-thing is within a vector
  first-thing)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn expand-body-parts
  "Expects a function to expand and a seq of maps that have a :name and :size"
  [fun body-parts]
  (reduce fun [] body-parts)
)

(def spider-parts [{:name "eye" :size 3 :count 8}
                   {:name "leg" :size 5 :count 16}])

(defn body-part-copy
  "Creates new body part copy with specified index"
  [part part-number]
  {:name (str part-number "-" (:name part))
   :size (:size part)})

(defn body-repeater
  "Creates new set containing copied body parts n-times"
  [part-count body-part]
  (loop [iteration 1
         result []]
    (if (> iteration part-count)
      result
      (recur (inc iteration) (into result [(body-part-copy body-part iteration)]))))
)

(defn spider-expander
  "Expands spider body parts"
  [final-body-parts part]
  (into final-body-parts (body-repeater (:count part) part)))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(defn add100
  [number]
  (+ number 100))

(defn dec-maker
  "Creates a custom value decrementor"
  [dec-value]
  #(- % dec-value)
)

(defn mapset
  [fun input]
  (set (map fun input)))

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(defn map-reduce
  [fun input]
  (reduce (fn [output f]
            (conj output (fun f)))
          []
          input))

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))
