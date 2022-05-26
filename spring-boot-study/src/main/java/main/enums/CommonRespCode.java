package main.enums;


import lombok.Getter;

/**
 * @author 志雄
 */
@Getter
public enum CommonRespCode {

    SUCCESS(200, "SUCCESS"),
    CREATED(201, "新资源被创建"),
    ACCEPTED(202, "已接受处理请求但尚未完成（异步处理）"),
    NULL(204, "请求失败"),
    MOVED_PERMANENTLY(301, "资源的URI已被更新"),
    SEE_OTHER(303, "其他（如，负载均衡）"),
    NOT_MODIFIED(304, "资源未更改（缓存）"),
    BAD_REQUEST(400, "坏请求（如，参数错误）"),
    NOT_FOUND(404, "资源不存在"),
    NOT_ACCEPTABLE(406, "服务端不支持所需表示"),
    CONFLICT(409, "通用冲突"),
    PRECONDITION_FAILED(412, "前置条件失败（如执行条件更新时的冲突）"),
    UNSUPPORTED_MEDIA_TYPE(415, " 接受到的媒体类型不受支持"),
    INTERNAL_SERVER_ERROR(500, "通用错误响应"),
    SERVICE_UNAVAILABLE(503, "服务端当前无法处理请求");

    private Integer code;
    private String msg;

    CommonRespCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static CommonRespCode getStateEnumById(Integer code) {
        for (CommonRespCode userStateEnum : CommonRespCode.values()) {
            if (code.equals(userStateEnum.getCode())) {
                return userStateEnum;
            }
        }
        return null;
    }
}
