(ns tech.ml.protocols.column)

(defprotocol PIsColumn
  (is-column? [item]))


(extend-protocol PIsColumn
  Object
  (is-column? [item] false))


(defprotocol PColumn
  (column-name [col])
  (set-name [col name]
    "Return a new column.")
  (supported-stats [col]
    "List of available stats for the column")
  (metadata [col]
    "Return the metadata map for this column.
    Metadata must contain :name :type :size.  Categorical
columns must have :categorical? true and the inference target
should have :target? true.")
  (set-metadata [col data-map]
    "Set the metadata on the column returning a new column.")
  (missing [col]
    "Indexes of missing values")
  (is-missing? [col idx]
    "Return true if this index is missing.")
  (set-missing [col long-rdr]
    "Set this group of indexes as the missing set")
  (unique [col]
    "Set of all unique values")
  (stats [col stats-set]
    "Return a map of stats.  Stats set is a set of the desired stats in keyword
form.  Guaranteed support across implementations for :mean :variance :median :skew.
Implementations should check their metadata before doing calculations.")
  (correlation [col other-column correlation-type]
    "Return the correlation coefficient
Supported types are:
:pearson
:spearman
:kendall")
  (select [col idx-seq]
    "Return a new column with the subset of indexes")
  (new-column [col datatype elem-count-or-values missing-set metadata]
    "Return a new column of this supertype with these values")
  (to-double-array [col error-on-missing?]
    "Convert to a java primitive array of a given datatype.  For strings,
an implicit string->double mapping is expected.  For booleans, true=1 false=0.
Finally, any missing values should be indicated by a NaN of the expected type."))
