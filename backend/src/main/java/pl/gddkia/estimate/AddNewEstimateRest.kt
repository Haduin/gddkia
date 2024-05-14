package pl.gddkia.estimate


data class AddNewEstimateRest(
    val companyName: String,
    val contractName: String,
    val dateFrom: String?,
    val dateTo: String?,
    val regionName: String,
    val branchName: String
)
