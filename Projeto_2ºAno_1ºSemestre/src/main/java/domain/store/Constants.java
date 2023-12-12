package domain.store;

public class Constants {
    public enum User {LOC_ID, LAT, LNG, USER_ID}
    public enum Distance {LOC_ID_1, LOC_ID_2, LENGTH}
    public static final String CSV_SPLIT_REGEX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
}
