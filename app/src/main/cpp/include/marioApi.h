
#ifndef MARIO_API_H
#define MARIO_API_H

#ifdef __cplusplus
extern "C" {
#endif

enum HOST_PLATFORM
{
      HUAWEI=1, 
	  RK,
};

#define MARIO_VERSION 1.0
		/*��ʼ������ƽ̨�����ӿ�ʵ�ֻ����ƽ̨�仯
		* return: 0:success  other:faild
		*/
		int marioInit(HOST_PLATFORM plat, const char* cachDir);

		/*����������Ϣ
		* return: 0:success  other:faild
		*/
		int setAttrBurst(const char* jsonStr);


		/*����GPS
		* return: 0:success  other:faild
		*/
		int setGps(double longitude, double latitude);

		/****************************************************************************/

		int getAttr(const char* key, char* description, size_t len);

		/****************************************************************************/

		/*check �ַ���
		* return: 0 success; else failed
		*/
		int checkAttr(const char* key, const char* value);
		
		/****************************************************************************/
		int clearApp(const char* packageName);
		
#ifdef __cplusplus
		}
#endif

#endif /*MARIO_API_H*/


