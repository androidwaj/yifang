/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lamp.ao_weibo.auth;

import java.io.ByteArrayOutputStream;

import android.content.Context;

import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * �첽�ص�����ࡣ
 * TODO����To be design...��
 * 
 * @author SINA
 * @since 2013-11-05
 */
public class AsyncWeiboRunner {
    /**
     * ��� URL �첽������ݣ����ڻ�ȡ����ݺ�ͨ�� {@link RequestListener} 
     * �ӿڽ��лص�����ע�⣺�ûص������������ں�̨�̵߳ġ�
     * ���⣬�ڵ��ø÷���ʱ���ɹ�ʱ������� {@link RequestListener#onComplete}��
     * {@link RequestListener#onComplete4binary} �����ᱻ�ص�������� {@link #request4Binary}��
     * 
     * @param url        ��������ַ
     * @param params     ��Ų��������
     * @param httpMethod "GET" or "POST"
     * @param listener   �ص�����
     */
    public static void request(
            final String url, 
            final WeiboParameters params, 
            final String httpMethod, 
            final RequestListener listener) {
        
        new Thread() {
            @Override
            public void run() {
                try {
                    String resp = HttpManager.openUrl(
                            url, httpMethod, params, params.getValue("pic"));
                    if (listener != null) {
                        listener.onComplete(resp);
                    }
                } catch (WeiboException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }.start();

    }

    /**
     * ��� URL �첽������ݣ����ڻ�ȡ����ݺ�ͨ�� {@link RequestListener} 
     * �ӿڽ��лص�����ע�⣺�ûص������������ں�̨�̵߳ġ�
     * ���⣬�ڵ��ø÷���ʱ���ɹ�ʱ������� {@link RequestListener#onComplete4binary}��
     * {@link RequestListener#onComplete} �����ᱻ�ص�������� {@link #request}��
     * 
     * @param url        ��������ַ
     * @param params     ��Ų��������
     * @param httpMethod "GET" or "POST"
     * @param listener   �ص�����
     */
    public static void request4Binary(
            final Context context, 
            final String url,
            final WeiboParameters params, 
            final String httpMethod, 
            final RequestListener listener) {
        
        new Thread() {
            @Override
            public void run() {
                try {
                    ByteArrayOutputStream resp = HttpManager.openUrl4Binary(
                            context, url, httpMethod, params, params.getValue("pic"));
                    if (listener != null) {
                        listener.onComplete4binary(resp);
                    }
                } catch (WeiboException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }.start();
    }
}
