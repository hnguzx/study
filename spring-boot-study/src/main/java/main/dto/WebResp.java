package main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.enums.CommonRespCode;

import java.io.Serializable;

/**
 * @author 志雄
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResp<T> implements Serializable {

    private CommonRespCode code;
    private String message;
    private T data;

    /**
     * 是否正常的响应
     *
     * @return true为正常返回
     */
    public boolean isOk() {
        return code == CommonRespCode.SUCCESS;
    }

    /**
     * 无data的正常返回
     *
     * @return 成功信息
     */
    public static <T> WebResp<T> retOk() {
        WebResp<T> response = new WebResp<>();
        response.setCode(CommonRespCode.SUCCESS);
        response.setMessage(CommonRespCode.SUCCESS.getMsg());
        return response;
    }

    /**
     * 有data的正常返回
     *
     * @param data 正确信息
     * @param <T>  信息类型
     * @return 正确信息
     */
    public static <T> WebResp<T> retOk(T data) {
        WebResp<T> response = new WebResp<>();
        response.setCode(CommonRespCode.SUCCESS);
        response.setMessage(CommonRespCode.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    /**
     * 无data的失败返回
     *
     * @param respCode 错误类型
     * @return 失败信息
     */
    public static <T> WebResp<T> retFail(CommonRespCode respCode) {
        return retFail(respCode, respCode.getMsg());
    }

    /**
     * 有data的失败返回
     *
     * @param respCode 错误类型
     * @param data  详细错误信息
     * @param <T>   泛型
     * @return 失败信息
     */
    public static <T> WebResp<T> retFail(CommonRespCode respCode, T data) {
        return retFail(respCode, respCode.getMsg(), data);
    }

    /**
     * 有data的失败返回
     *
     * @param data  详细错误信息
     * @param <T>   泛型
     * @return 失败信息
     */
    public static <T> WebResp<T> retFail(T data) {
        WebResp<T> response = new WebResp<>();
        response.setCode(CommonRespCode.INTERNAL_SERVER_ERROR);
        response.setMessage(CommonRespCode.INTERNAL_SERVER_ERROR.getMsg());
        response.setData(data);
        return response;
    }

    /**
     * 无data的失败返回
     *
     * @param respCode 错误码
     * @param msg  错误信息
     * @param <T>  泛型
     * @return 失败信息
     */
    public static <T> WebResp<T> retFail(CommonRespCode respCode, String msg) {
        WebResp<T> response = new WebResp<>();
        response.setCode(respCode);
        response.setMessage(msg);
        return response;
    }

    /**
     * 有data的失败返回
     *
     * @param respCode 错误码
     * @param msg  错误信息
     * @param data 实际对象
     * @param <T>  实际类型
     * @return 失败信息
     */
    public static <T> WebResp<T> retFail(CommonRespCode respCode, String msg, T data) {
        WebResp<T> response = new WebResp<>();
        response.setCode(respCode);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }
}
