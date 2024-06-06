(ns cider-middleware-out-repro-issue-878
  (:require [clojure.tools.logging :as log]))

(defn log-forever
  []
  (while true
    (log/info "Logging forever")
    (Thread/sleep 1000)))

(comment
  (log-forever)
  )
