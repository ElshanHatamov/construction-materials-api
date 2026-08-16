# Construction Materials API

Tikinti materiallarının idarə edilməsi, istifadəçi autentifikasiyası, inventar filtrlənməsi, fayl idarəetməsi və avtomatlaşdırılmış fon tapşırıqları üçün **Spring Boot** ilə qurulmuş güclü və miqyaslana bilən RESTful backend tətbiqi.

---

## 🚀 Texnologiya Yığını (Tech Stack)

* **Java** (17+)
* **Spring Boot** (Web, Data JPA, Security, Cache, Validation, Scheduling)
* **PostgreSQL** (Relational Database)
* **Hibernate** (ORM)
* **Docker & Docker Compose** (Konteynerləşdirmə)
* **Gradle** (Build Aləti)
* **JUnit & Mockito** (Unit Testlər)

---

## ✨ Əsas Xüsusiyyətlər

* **Təhlükəsiz Autentifikasiya və Avtorizasiya:** Spring Security və JWT (JSON Web Token) əsaslı stateless təhlükəsizlik mexanizmi.
* **Məhsul İdarəetməsi və Filtrləmə:** Məhsulların ad, kateqoriya və qiymət aralığına görə dinamik filtrləməsi, həmçinin səhifələmə (`pagination`) dəstəyi.
* **Performans Optimizasiyası (Caching):** Verilənlər bazasına düşən yükü azaltmaq üçün yüksək tezlikli oxuma sorğularında Spring Cache abstraksiyasının inteqrasiyası.
* **Fayl İdarəetməsi:** Multipart fayl yükləmə və təhlükəsiz endirmə endpoint-ləri. Ciddi fayl tipi (`JPEG`, `PNG`) və ölçü validasiyası (`maks 5MB`).
* **Avtomatlaşdırılmış Fon Tapşırıqları:** Köhnəlmiş və ya müvəqqəti faylların gündəlik avtomatik təmizlənməsi üçün `@Scheduled` əsaslı cron tapşırıqları.
* **Qlobal Xəta İdarəetməsi:** Təmiz və strukturlaşdırılmış xəta mesajları qaytaran qlobal xəta idarəetmə mexanizmi.

---

## 📌 API Endpoint-ləri (API Documentation)

### 1. Autentifikasiya (Auth)
| Metod | Endpoint | Təsvir | Sorğu Nümunəsi / Status |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Yeni istifadəçi qeydiyyatı | Public |
| `POST` | `/api/auth/login` | Sistemə daxil olmaq və JWT token almaq | Public |

### 2. Məhsullar (Products)
| Metod | Endpoint | Təsvir | Tələb olunan İcazə |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/products` | Bütün məhsulları səhifələmə və filtrləmə ilə gətirmək (Cached) | Public |
| `GET` | `/api/products/{id}` | ID-yə görə məhsul detallarını əldə etmək | Public |
| `POST` | `/api/products` | Yeni tikinti materialı əlavə etmək | Authenticated (Admin) |
| `PUT` | `/api/products/{id}` | Mövcud məhsul məlumatlarını yeniləmək | Authenticated (Admin) |
| `DELETE` | `/api/products/{id}` | Məhsulu sistemdən silmək | Authenticated (Admin) |

### 3. Fayl İdarəetməsi (Files)
| Metod | Endpoint | Təsvir | Qeyd |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/files/upload` | Şəkil faylı yükləmək (Multipart) | Yalnız JPEG/PNG, max 5MB |
| `GET` | `/api/files/download/{fileName}` | Serverdən faylı təhlükəsiz endirmək | Stream / Resource |

---

## 🛠️ Layihəni İşə Salmaq (Getting Started)

### Tələblər
Kompüterinizdə aşağıdakı alətlərin quraşdırıldığından əmin olun:
* Java JDK 17 və ya daha yuxarı versiya
* Gradle
* PostgreSQL
* Docker (isteğe bağlı, konteynerləşdirmə üçün)

### Quraşdırma və Çalışdırma

1. **Repozitoriyanı klonlayın:**
   ```bash
   git clone [https://github.com/ElshanHatamov/construction-materials-api.git](https://github.com/ElshanHatamov/construction-materials-api.git)
   cd construction-materials-api
