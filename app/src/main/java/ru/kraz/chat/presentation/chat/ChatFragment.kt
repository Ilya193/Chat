package ru.kraz.chat.presentation.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.adapter
import com.elveum.elementadapter.addBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.chat.databinding.FragmentChatBinding
import ru.kraz.chat.databinding.RecipientMessageLayoutBinding
import ru.kraz.chat.databinding.SenderMessageLayoutBinding
import ru.kraz.chat.presentation.BaseFragment
import java.io.File

class ChatFragment : BaseFragment() {
    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModel()

    private var nickname = ""

    private val adapter: SimpleBindingAdapter<MessageUi> by lazy {
        adapter {
            addBinding<MessageUi.Sender, SenderMessageLayoutBinding> {
                areItemsSame = { oldItem, newItem -> oldItem.same(newItem) }
                areContentsSame = { oldItem, newItem -> oldItem.sameContent(newItem) }

                bind {
                    tvNickname.text = it.senderNickname
                    tvMessage.text = it.message
                    if (it.photo.isNotEmpty()) {
                        image.visibility = View.VISIBLE
                        image.load(it.photo)
                    } else image.visibility = View.GONE
                }
            }

            addBinding<MessageUi.Recipient, RecipientMessageLayoutBinding> {
                areItemsSame = { oldItem, newItem -> oldItem.same(newItem) }
                areContentsSame = { oldItem, newItem -> oldItem.sameContent(newItem) }

                bind {
                    tvNickname.text = it.senderNickname
                    tvMessage.text = it.message
                    if (it.photo.isNotEmpty()) {
                        image.visibility = View.VISIBLE
                        image.load(it.photo)
                    } else image.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nickname = arguments?.getString("nickname", "") ?: ""
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingViewModel()
        setClickListeners()
        binding.rvMessages.adapter = adapter
    }

    override fun settingViewModel() {
        chatViewModel.fetchMessage()
        chatViewModel.chatResult.observe(viewLifecycleOwner) {
            when (it) {
                is ChatState.Success -> renderSuccess(it)
                is ChatState.Loading -> renderLoading()
                is ChatState.Error -> renderError(it)
            }
        }
    }

    private fun renderSuccess(state: ChatState.Success) {
        binding.errorContainer.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.rvMessages.visibility = View.VISIBLE
        adapter.submitList(state.data)
        binding.rvMessages.postDelayed({
            binding.rvMessages.scrollToPosition(0)
        }, 10)
    }

    private fun renderError(state: ChatState.Error) {
        binding.loading.visibility = View.GONE
        binding.rvMessages.visibility = View.GONE
        binding.errorContainer.visibility = View.VISIBLE
        binding.tvError.text = state.msg
    }

    private fun renderLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.rvMessages.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE
    }

    override fun setClickListeners() {
        binding.icSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                if (binding.changeImage.visibility == View.VISIBLE) {
                    chatViewModel.sendMessageWithImage(message, filename, imgUri.toString())
                    dataCleaning()
                } else chatViewModel.sendMessage(message)
            } else {
                if (binding.changeImage.visibility == View.VISIBLE) {
                    chatViewModel.sendMessageWithImage(message, filename, imgUri.toString())
                    dataCleaning()
                }
            }
            binding.etMessage.setText("")
        }

        binding.icAttachFile.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        binding.icCloseImage.setOnClickListener {
            dataCleaning()
        }
    }

    private fun dataCleaning() {
        binding.image.setImageURI(null)
        binding.changeImage.visibility = View.GONE
        filename = ""
        imgUri = null
    }

    private var filename = ""
    private var imgUri: Uri? = null

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                imgUri = data?.data!!
                filename = File(imgUri.toString()).name
                binding.changeImage.visibility = View.VISIBLE
                binding.image.setImageURI(imgUri)
            }
        }

    companion object {
        fun newInstance(nickname: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString("nickname", nickname)
                }
            }
    }
}