;;
;; Distance functions
;; @author Antonio Garrote
;;

(ns clj-ml.distance-functions
  (:use [clj-ml utils data])
  (:import (weka.core EuclideanDistance ManhattanDistance ChebyshevDistance)))


;; Setting up clusterer options

(defn- make-distance-function-options
  "Creates ther right parameters for a distance-function"
  ([map]
     (let [cols (get map :attributes)
           pre-cols (reduce #(str %1 "," (+ %2 1)) "" cols)
           cols-val-a ["-R" (.substring pre-cols 1 (.length pre-cols))]
           cols-val-b (check-options {:invert "-V"
                                      :no-normalization "-D"}
                                     map
                                     cols-val-a)]
       (into-array cols-val-b))))


(defmulti make-distance-function
  "Creates a new distance function"
  (fn [kind & options] kind))


(defmethod make-distance-function :euclidean
  ([kind & options]
     (let [dist (new EuclideanDistance)
           opts (make-distance-function-options (first-or-default options {}))]
       (.setOptions dist opts)
       dist)))

(defmethod make-distance-function :manhattan
  ([kind & options]
     (let [dist (new ManhattanDistance)
           opts (make-distance-function-options (first-or-default options {}))]
       (.setOptions dist opts)
       dist)))

(defmethod make-distance-function :chebyshev
  ([kind & options]
     (let [dist (new ChebyshevDistance)
           opts (make-distance-function-options (first-or-default options {}))]
       (.setOptions dist opts)
       dist)))

