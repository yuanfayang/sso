package com.changhong.sso.common.core.authentication;

import com.changhong.sso.common.core.entity.EncryCredentialInfo;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @Package : com.changhong.sso.common.core.authentication
 * @Author : Fayang Yuan
 * @Email : fayang.yuan@changhong.com ,flyyuanfayang@sina.com
 * @Date : 16/3/25
 * @Time : 下午1:23
 * @Description :
 */
public class EncryCredentialManagerImplTest extends TestCase {

    public void testSetKeyService() throws Exception {

    }

    public void testDecrypt() throws Exception {

    }

    public void testEncrypt() throws Exception {

    }

    public void testCheckEncryCredentialInfo() throws Exception {
        //String clientEc="cTc4aUc4TjZVZFVFQStwN2RRb2g3UnBabWVhemp1NzNmMlBiRk40c3Z3Z0VRTnpDaGVoVDEyVFhZ%0AV1RvbGYrQ3dKaHlSdVhBNkQzUQplWkkyWnQ2MkxibHEralcrR3BHQWNDeFp5RE5ydEZsdkxxdXc3%0AMzBQYTRLV2Y0Z2NuUnR0cFhOQ29Cc2VHUFZ4K3JzVWZMTGJHVWlXClJhdkVEdHkxVTc4WDFWSHFr%0AeG9kbWdFcjlvQjZLNkhkai83RlIwR2ord2kwQ3NoaEtkdno5OGdZU0hUdWFWcUJuN3BkcU90U0lW%0ATy8KRXlmRlhaQUxEaC81Q0R5ZnhmdktzWmpkUmtFTjYzZU9UcHV2a2FBdkZ5V21iSnJxWXBVTWFu%0ANStEdEhJTFNBbmlIZVA3Vk4wVkZLdApGeDJEWVk3RmdZdnprNlFYdUtRa1BaMlcyaENuYWQ4VVZ5%0ATGhFc1BVdEFVc1RWVnJiTUdhWlo1d2JuRUZKMmRGTUpqU1VBPT0%2FYXBwSWQ9NjJjYmMxYjNhNGRk%0ANDQxMWJmZjQ3NTliY2IwOTVhNjEma2V5SWQ9MQ%3D%3D";
        String clientEc="Y2U5Sm1nWi9mMTlpZHpBbVhmdjJhS3lUcUFCMGFqYzZBcklDdDJtZHVOekVSZC91SWlQVi9heEky UitMQ2xpRXo2czRrUnk4WXR4bwpXbjAyZHpUKzdNem8wWi9hSnJFYldlbEliK2xJaWJRU253Q1Uv TnduUzBPTzFmeUU4aklkcXpEMFRRYjRTSnlNYWwwS0c4WTZmdFZICnVSVzh4bWlKYmZiTVUzVzBY ZUFXZmM5RmdOTHFSN2hYNTZkWFphaXEzK0pwcG9tdVB4THRoUzlWMTluNVNmWG8yQVk1MmJ2REwx VWEKN1gzVVl5RVBvT3Fac0VlRitKM2NVSkYyNWlaV1lqTkQxZExPRDJVaUhCZDdDS21rc0F3QUVU NW5RYk80R2lVUkdkVU1PVzRlU0pycQpvaURGUWxHZkhXaG1DQnZXK0ZzVEh4dytja0Zaa0o3ckow TEhxMlEvb3FXWXA0Z2xaZDQ3WGd2Rmw0MUdHdEZEY285MEN3PT0/YXBwSWQ9ZGUzNDg5YjMzZmQ1 NGJkZGFkNGYwYzU2NDFiMWI3NjAma2V5SWQ9NA==";
        EncryCredential encryCredential=new EncryCredential(clientEc);

        EncryCredentialManagerImpl encryCredentialManager=new EncryCredentialManagerImpl();
        EncryCredentialInfo encryCredentialInfo=encryCredentialManager.decrypt(encryCredential);
    }
}