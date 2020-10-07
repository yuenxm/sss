
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
		/*初始化宿主平台，各接口实现会根据平台变化
		* return: 0:success  other:faild
		*/
		int marioInit(HOST_PLATFORM plat, const char* cachDir);

		/*设置属性信息
		* return: 0:success  other:faild
		*/
		int setAttrBurst(const char* jsonStr);


		/*设置GPS
		* return: 0:success  other:faild
		*/
		int setGps(double longitude, double latitude);

		/****************************************************************************/

		int getAttr(const char* key, char* description, size_t len);

		/****************************************************************************/

		/*check 字符串
		* return: 0 success; else failed
		*/
		int checkAttr(const char* key, const char* value);
		
		/****************************************************************************/
		int clearApp(const char* packageName);
		
#ifdef __cplusplus
		}
#endif

#endif /*MARIO_API_H*/


