package com.hrtzpi.helpers;

import com.hrtzpi.models.area_models.AreaResponse;
import com.hrtzpi.models.cart.AddCartResponse;
import com.hrtzpi.models.cart.CartResponse;
import com.hrtzpi.models.cart.delete_cart_models.DeleteCartResponse;
import com.hrtzpi.models.category_models.CategoryResponse;
import com.hrtzpi.models.gifts.additional.AdditionalResponse;
import com.hrtzpi.models.gifts.covers.CoverResponse;
import com.hrtzpi.models.gifts.messages.MessageGiftResponse;
import com.hrtzpi.models.login_models.EditNameResponse;
import com.hrtzpi.models.login_models.LogInSendModel;
import com.hrtzpi.models.login_models.LoginResponse;
import com.hrtzpi.models.registration_models.RegistrationResponse;
import com.hrtzpi.models.registration_models.RegistrationSendModel;
import com.hrtzpi.models.search_products.ProductsResponse;
import com.hrtzpi.models.slider_models.SliderResponse;
import com.hrtzpi.models.storeorder.ModelStoreOrder;
import com.hrtzpi.models.video_models.VideoResponse;
import com.hrtzpi.models.wishlist_models.WishlistResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import static com.hrtzpi.helpers.StaticMembers.GIFT_ADDITION_IDS;
import static com.hrtzpi.helpers.StaticMembers.GIFT_QUANTITY_ADDITION;

public interface ApiInterface {


    @GET(StaticMembers.CATEGORY)
    Call<CategoryResponse> getCategories();

    @GET(StaticMembers.SLIDER)
    Call<SliderResponse> getSlider();

    @GET(StaticMembers.PRODUCT)
    Call<ProductsResponse> getProducts(@QueryMap HashMap<String, String> params);

    @POST(StaticMembers.login)
    Call<LoginResponse> login(@Body LogInSendModel logInSendModel);

    @POST(StaticMembers.register)
    Call<RegistrationResponse> register(@Body RegistrationSendModel registrationSendModel);

    @POST(StaticMembers.WISHLIST_ACTION)
    Call<WishlistResponse> toggleWishlist(@Path(StaticMembers.ID) long id);

    @GET(StaticMembers.WISHLIST)
    Call<ProductsResponse> getWishList();

    @GET(StaticMembers.AREA)
    Call<AreaResponse> getAreas();

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_NAME)
    Call<EditNameResponse> editField(@FieldMap HashMap<String, String> params);

    @GET(StaticMembers.CART)
    Call<CartResponse> getCart();

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_CART)
    Call<AddCartResponse> addOrEditCart(@FieldMap Map<String, String> params
            , @Field(GIFT_ADDITION_IDS) List<String> giftsAdditionals
            , @Field(GIFT_QUANTITY_ADDITION) List<String> quantityAdditionals);

    @FormUrlEncoded
    @POST(StaticMembers.EDIT_CART)
    Call<AddCartResponse> addOrEditCart(@Field(StaticMembers.PRODUCT_ID) String id, @Field(StaticMembers.QUANTITY) int quantity);

    @POST(StaticMembers.DELETE_CART)
    Call<DeleteCartResponse> deleteCartItem(@Path(StaticMembers.ID) String id);

    @GET(StaticMembers.VIDEO)
    Call<VideoResponse> getVideoList();

    @GET(StaticMembers.GIFT_COVER)
    Call<CoverResponse> getCovers();

    @GET(StaticMembers.GIFT_ADDITION)
    Call<AdditionalResponse> getAdditions();

    @GET(StaticMembers.GIFT_MESSAGE)
    Call<MessageGiftResponse> getMessages();

    @FormUrlEncoded
    @POST(StaticMembers.Make_Order)
    Call<ModelStoreOrder> storeOrder(@FieldMap HashMap<String, String> params);

    /*
    @GET(StaticMembers.GIFT_GET)
    Call<GiftsResponse> getGifts();

    @GET(StaticMembers.OFFERS)
    Call<OffersResponse> getOffers();

    @FormUrlEncoded
    @POST(StaticMembers.CONTACT)
    Call<MessageResponse> sendMessage(@Field(StaticMembers.MESSAGE) String message);

    @FormUrlEncoded
    @POST(StaticMembers.GIFT)
    Call<MessageResponse> addPromo(@Field(StaticMembers.CODE) String code);
*/
}
