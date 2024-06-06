(ns cider-middleware-out-repro-issue-878
  (:require [clojure.tools.logging :as log]))

(defn make-log-forever-future
  [& _]
  (future
    (while true
    (log/info "Logging forever")
    (Thread/sleep 1000))))

(defonce log-forever-future (make-log-forever-future))

(comment
  (log/info
   "I always show up but I don't cause previous connection's `log-forever-future`s to show back up")

  (if (future-cancelled? log-forever-future)
    (alter-var-root #'log-forever-future make-log-forever-future)
    (log/info "Future not cancelled. Not altering"))

  (future-cancel log-forever-future)
  )
