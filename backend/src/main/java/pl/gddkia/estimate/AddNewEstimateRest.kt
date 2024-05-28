package pl.gddkia.estimate


data class AddNewEstimateRest(
    val companyName: String,
    val contractName: String,
    val dateFrom: String?,
    val dateTo: String?,
    val branchName: String,
    val regionName: String,
    val sectionName: String,
    val roadLength: Long
)
