package pl.gddkia.job;


public class JobMapper {
    public static JobRest mapToRest(Jobs job){
        if(job!=null){
            return new JobRest(
                    job.getSST(),
                    job.getDescription(),
                    job.getUnit(),
                    job.getCostEstimate(),
                    job.getQuantity(),
                    job.getGroupType(),
                    job.getSubType()
            );
        }
        return null;
    }
}
